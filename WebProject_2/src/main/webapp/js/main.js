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
        let rNumber = $('#r-text').val().replace(',', '.'); // Sửa lại để khớp với id của R

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
            draw($('#x-text').val() * 68 / rVal + 110, -($('#r-text').val() / rVal * 68 - 110));
        } else {
            clearCanvas();
        }
    }

    canvas.on('click', function (event) {
        if (!validateR()) return;

        let xFromCV = (event.offsetX - 110) / 68 * rVal;
        let minDiff = Infinity;
        let nearestXVal;

        for (let i = 0; i < X_VALUES.length; i++) {
            if (Math.abs(xFromCV - X_VALUES[i]) < minDiff) {
                minDiff = Math.abs(xFromCV - X_VALUES[i]);
                nearestXVal = X_VALUES[i];
            }
        }

        let yVal = (-event.offsetY + 110) / 68 * rVal;
        if (yVal < Y_VALUES[0]) yVal = Y_VALUES[0];
        else if (yVal > Y_VALUES[Y_VALUES.length - 1]) yVal = Y_VALUES[Y_VALUES.length - 1];

        draw(nearestXVal * 68 / rVal + 110, -(yVal / rVal * 68 - 110));

        // Gán giá trị X và Y vào input của form
        $('#x-text').val(nearestXVal); // Gán giá trị X gần nhất vào input
        $('input[name="yVal"]').prop('checked', false); // Bỏ chọn tất cả checkbox Y
        $(`input[name="yVal"][value="${yVal}"]`).prop('checked', true); // Chọn checkbox tương ứng với giá trị Y gần nhất
    });

    $('.input-form-pane-buttons-button-submit').on('click', function (event) {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            $('.input-form-hidden-r').val(rVal);
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
            svgGraph.querySelector('.coordinate-text-minus-Rx-half').textContent = (-rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-minus-Ry-half').textContent = (-rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-plus-Rx').textContent = (rVal).toString();
            svgGraph.querySelector('.coordinate-text-plus-Ry').textContent = (rVal).toString();
            svgGraph.querySelector('.coordinate-text-plus-Rx-half').textContent = (rVal / 2).toString();
            svgGraph.querySelector('.coordinate-text-plus-Ry-half').textContent = (rVal / 2).toString();

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
