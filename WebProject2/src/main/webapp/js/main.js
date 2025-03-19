$(function () {
    const Y_VALUES = [-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0];
    const X_MIN = -5;
    const X_MAX = 5;
    const R_MIN = 2;
    const R_MAX = 5;

    let rVal;
    let canvas = $('.graph-canvas');

    function isNumberic(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }

    function validateX() {
        let xNumber = $('#x-text').val().replace(',', '.'); // Sửa lại để khớp với id của X

        if (isNumberic(xNumber) && xNumber >= X_MIN && xNumber <= X_MAX) {
            $('.input-form-info').text('Enter X');
            return true;
        } else {
            $('.input-form-info').text('Enter a X value between -5 and 5!');
            return false;
        }
    }

    function validateY() {
        let selectedYValues = [];

        // Lấy tất cả các checkbox đã được chọn
        $('input[name="yVal"]:checked').each(function () {
            selectedYValues.push(parseFloat($(this).val()));
        });

        if (selectedYValues.length === 0) {
            $('.input-form-info').text('Please select one Y value!');
            return false;
        }

        if (selectedYValues.length > 1) {
            $('.input-form-info').text('You can only select one Y value!');
            return false;
        }

        let yVal = selectedYValues[0];
        if (Y_VALUES.includes(yVal)) {
            $('.input-form-info').text('Y value selected: ' + yVal);
            return true;
        } else {
            $('.input-form-info').text('Select a valid Y value!');
            return false;
        }
    }

    function validateR() {
        let rNumber = $('#r-text').val().replace(',', '.');

        if (isNumberic(rNumber) && rNumber >= R_MIN && rNumber <= R_MAX) {
            $('.input-form-info').text('Enter R');
            return true;
        } else {
            $('.input-form-info').text('Enter a R value between 2 and 5!');
            return false;
        }
    }

    function validateForm() {
        return validateX() && validateY() && validateR();
    }

    function draw(x, y) {
        clearCanvas();
        if (x > canvas.width() || x < -canvas.width() || y > canvas.height() || y < -canvas.height()) return;
        let ctxAxes = canvas[0].getContext('2d');
        ctxAxes.setLineDash([2, 2]);
        ctxAxes.beginPath();
        ctxAxes.moveTo(x, 110);
        ctxAxes.lineTo(x, y);
        ctxAxes.moveTo(110, y);
        ctxAxes.lineTo(x, y);
        ctxAxes.stroke();
        ctxAxes.fillStyle = 'grey';
        ctxAxes.arc(x, y, 2, 0, 2 * Math.PI);
        ctxAxes.fill();
    }

    function clearCanvas() {
        canvas[0].getContext('2d').clearRect(0, 0, canvas.width(), canvas.height());
    }

    function readFromInput() {
        if (validateForm()) {
            let yVal = $('input[name="yVal"]:checked').val(); // Get the selected Y value
            draw($('#x-text').val() * 68 / rVal + 110, -((yVal / rVal) * 68 - 110)); // Use yVal instead of r-text
        } else {
            clearCanvas();
        }
    }

    canvas.on('click', function (event) {
        if (!validateR()) return;

        let xFromCV = (event.offsetX - 110) / 68 * rVal;
        let minDiffX = Infinity;
        let nearestXVal;

        // Find the nearest X value
        for (let i = X_MIN; i <= X_MAX; i++) {
            if (Math.abs(xFromCV - i) < minDiffX) {
                minDiffX = Math.abs(xFromCV - i);
                nearestXVal = i;
            }
        }

        // Get the y-value corresponding to the mouse click
        let yFromCV = (-event.offsetY + 110) / 68 * rVal;
        let minDiffY = Infinity;
        let nearestYVal;

        // Find the nearest Y value
        for (let i = 0; i < Y_VALUES.length; i++) {
            if (Math.abs(yFromCV - Y_VALUES[i]) < minDiffY) {
                minDiffY = Math.abs(yFromCV - Y_VALUES[i]);
                nearestYVal = Y_VALUES[i];
            }
        }

        // Draw point on the canvas at the nearest valid (X, Y) coordinates
        draw(nearestXVal * 68 / rVal + 110, -(nearestYVal / rVal * 68 - 110));

        // Update the input fields with the nearest X and Y values
        $('#x-text').val(nearestXVal);
        $('input[name="yVal"]').prop('checked', false);
        $(`input[name="yVal"][value="${nearestYVal}"]`).prop('checked', true);
    });


    $('.input-form-pane-buttons-button-submit').on('click', function (event) {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            $('.input-form-hidden-timezone').val(new Date().getTimezoneOffset());
        }
    });

    $('.input-form-pane-buttons-button-reset').on('click', function (event) {
        $('.input-form-hidden-clear').val(true);
    });

    $('#r-text').on('input', function (event) {
        rVal = $(this).val(); // Lấy giá trị từ input text của R

        if (isNumberic(rVal) && rVal >= R_MIN && rVal <= R_MAX) {
            $(this).removeClass('input-form-invalid');
            let svgGraph = document.querySelector(".result-graph").getSVGDocument();

            svgGraph.querySelector('.coordinate-text-minus-Rx').textContent = (-rVal).toString();
            svgGraph.querySelector('.coordinate-text-minus-Ry').textContent = (-rVal).toString();
            svgGraph.querySelector('.coordinate-text-minus-half-Rx').textContent = (-rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-minus-half-Ry').textContent = (-rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-plus-Rx').textContent = (rVal).toString();
            svgGraph.querySelector('.coordinate-text-plus-Ry').textContent = (rVal).toString();
            svgGraph.querySelector('.coordinate-text-plus-half-Rx').textContent = (rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-plus-half-Ry').textContent = (rVal / 2).toString();

            readFromInput();
        } else {
            $(this).addClass('input-form-invalid');
        }
    });


    // Bắt sự kiện khi giá trị Y thay đổi
    $('input[name="yVal"]').on('change', event => readFromInput());

    // Bắt sự kiện khi giá trị X thay đổi
    $('#x-text').on('input', event => readFromInput());
});
