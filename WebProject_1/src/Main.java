import com.fastcgi.FCGIInterface;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        var fcgi = new FCGIInterface();
        List<Double> validX = List.of(-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0);
        List<Double> validR = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        while (fcgi.FCGIaccept() >= 0) {
            String request = null;
            try {
                request = readRequest();
            } catch (IOException e) {
                String httpResponse = """
                        HTTP/1.1 400 Bad Request
                        Content-Type: text/plain
            
                        Bad Request!
                        """;
                System.out.println(httpResponse);
            }

            try {
                FastCGIRequest fRequest = gson.fromJson(request, FastCGIRequest.class);
                double x = fRequest.getX();
                double y = fRequest.getY();
                double r = fRequest.getR();

                if (!validX.contains(x) || y < -5 || y > 5 || !validR.contains(r)) {
                    String httpResponse = """
                HTTP/1.1 400 Bad Request
                Content-Type: text/plain
                
                Bad Request
                """;
                    System.out.println(httpResponse);
                }

                FastCGIResponse fResponse = FastCGIResponse.createNewResponse(fRequest);
                String response = gson.toJson(fResponse, FastCGIResponse.class);
                var httpResponse = """
                        HTTP/1.1 200 OK
                        Content-Type: application/json
                                            
                        %s
                        """.formatted(response);
                System.out.println(httpResponse);
            } catch (NumberFormatException | JsonSyntaxException e) {
                String httpResponse = """
                        HTTP/1.1 400 Bad Request
                        Content-Type: text/plain
            
                        Bad Request: X, Y, or R is not a valid number
                        """;
                System.out.println(httpResponse);
            }
        }
    }

    private static String readRequest() throws IOException {
        FCGIInterface.request.inStream.fill();
        var ctLength = FCGIInterface.request.inStream.available();
        var bf = ByteBuffer.allocate(ctLength);
        var readBytes = FCGIInterface.request.inStream.read(bf.array(), 0, ctLength);
        var rqBody = new byte[readBytes];
        bf.get(rqBody);
        bf.clear();
        return new String(rqBody, StandardCharsets.UTF_8);
    }
}