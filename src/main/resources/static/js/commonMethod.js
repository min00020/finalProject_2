 
function showBasicAlert(title, text) {
	Swal.fire({
		title : title,
		text : text,
		confirmButtonText : "확인"
    });
}

function showInfoAlert(title, text) {
	Swal.fire({
		title : title,
		text : text,
		icon: 'info',
		confirmButtonText : "확인"
	})
}
function showSuccessAlert(title,text) {
	Swal.fire({
		title : title,
		text : text,
		icon: 'success',
		confirmButtonText : "확인"
	})
}
function showFailAlert(title, text) {
	Swal.fire({
		title : title,
		text : text,
		icon: 'error',
		confirmButtonText : "확인"
	})
}
function showWarningAlert(title, text) {
	Swal.fire({
		title : title,
		text : text,
		icon: 'warning',
		confirmButtonText : "확인"
	})
}
