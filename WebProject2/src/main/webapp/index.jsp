<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<jsp:useBean id="entries" class="beans.EntriesBean" scope="session" />

<html lang="ru">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewpoint" content="width=device-width, initial-scale=1.0">
    <title>Web Project #2</title>
</head>

<body>
<header class="main-header topper clearfix">
        <span class="main-header-info">
            Web Project #2 <br>
            93835
        </span>
    <span class="main-header-creator">
            Фан Нгок Туан (P3221)
        </span>
</header>

<main class="main-container">
    <h1 class="blur-visual" > Web Project #2</h1>

    <div class="main-container-item main-container-left-column">
        <section class="main-container-left-column-item content-section content-section-graph">
            <h2 class="content-section-header content-section-graph topper">
                    <span class="content-section-header-text">
                        Graph
                    </span>
            </h2>
            <div class="content-container result-graph-container">
                <object class="result-graph" type="image/svg+xml" data="img/result-graph.svg"></object>
                <canvas class="graph-canvas" width="220" height="220">Interactive Graph</canvas>
            </div>
        </section>

        <section class="main-container-item-left-col-item content-section content-section-val">
            <h2 class="content-section-header content-section-val topper">
                    <span class="content-section-header-text">
                        Data
                    </span>
            </h2>
            <form class="input-form" action="controller" method="GET">
                <p class="input-form-info">Enter all values</p>

                <div class="input-form-container input-form-container-text">
                    <label class="input-form-label input-form-label-x" for="x-text">X:</label>
                    <input class="input-form-pane input-form-text input-form-text-x" id="x-text" type="text" name="xVal" maxlength="10" autocomplete="off" placeholder="Value form -5 to 5">
                </div>

                <div class="input-form-container input-form-container-checkbox">
                    <label class="input-form-label input-form-label-y">Y:</label>
                    <div class="input-form-checkbox-group">
                        <input type="checkbox" name="yVal" value ="-2" id="y-checkbox-1">
                        <label for="y-checkbox-1">-2</label>
                        <input type="checkbox" name="yVal" value ="-1.5" id="y-checkbox-2">
                        <label for="y-checkbox-2">-1.5</label>
                        <input type="checkbox" name="yVal" value ="-1" id="y-checkbox-3">
                        <label for="y-checkbox-3">-1</label>
                        <input type="checkbox" name="yVal" value ="-0.5" id="y-checkbox-4">
                        <label for="y-checkbox-4">-0.5</label>
                        <input type="checkbox" name="yVal" value ="0" id="y-checkbox-5">
                        <label for="y-checkbox-5">0</label>
                        <input type="checkbox" name="yVal" value ="0.5" id="y-checkbox-6">
                        <label for="y-checkbox-6">0.5</label>
                        <input type="checkbox" name="yVal" value ="1" id="y-checkbox-7">
                        <label for="y-checkbox-7">1</label>
                        <input type="checkbox" name="yVal" value ="1.5" id="y-checkbox-8">
                        <label for="y-checkbox-8">1.5</label>
                        <input type="checkbox" name="yVal" value ="2" id="y-checkbox-9">
                        <label for="y-checkbox-9">2</label>
                    </div>
                </div>
                <div class="input-form-container input-form-container-text">
                    <label class="input-form-label input-form-label-r" for="r-text">R:</label>
                    <input class="input-form-pane input-form-text input-form-text-r" id="r-text" type="text" name="rVal" maxlength="10" autocomplete="off" placeholder="Value form 2 to 5">
                </div>

                <input class="input-form-hidden-timezone" type="hidden" name="timezone" value="">
                <input class="input-form-hidden-clear" type="hidden" name="clear" value="">

                <div class="input-form-pane-buttons-container">
                    <button class="input-form-pane-buttons-button input-form-pane-buttons-button-submit" type="submit">Submit</button>
                    <button class="input-form-pane-buttons-button input-form-pane-buttons-button-reset" type="submit">Reset</button>
                </div>
            </form>
        </section>
    </div>

    <section class="main-container-item main-container-item-table content-section content-section-table">
        <h2 class="content-section-header content-section-header-table topper">
                <span class="content-section-header-text">
                    Table
                </span>
        </h2>

        <div class="result-table-container">
            <table class="result-table">
                <tr class="result-table-header">
                    <th class="result-table-coords-col">X</th>
                    <th class="result-table-coords-col">Y</th>
                    <th class="result-table-coords-col">R</th>
                    <th class="result-table-time-col">Current Time</th>
                    <th class="result-table-time-col">Execution Time</th>
                    <th class="result-table-hits-col">Result</th>
                </tr>
                <c:forEach var="entry" items="${entries.entries}">
                    <tr>
                        <td>${entry.xVal}</td>
                        <td>${entry.yVal}</td>
                        <td>${entry.rVal}</td>
                        <td>${entry.currentTime}</td>
                        <td>${entry.executionTime}</td>
                        <td>${entry.hitResult}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>
