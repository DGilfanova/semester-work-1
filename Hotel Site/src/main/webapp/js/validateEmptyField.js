let form = document.querySelector('.validateOnEmptyField')
let fields = form.querySelectorAll(".field")

form.addEventListener('submit', function (event) {

    removeErrors()

    if (checkFieldsPresence()) {
        event.preventDefault()
        return
    }
})

function checkFieldsPresence () {
    let flag = false
    for (let i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            let error = generateError('Please enter info')
            form[i].parentElement.insertBefore(error, fields[i])
            flag = true
        }
    }
    return flag
}

function generateError (text) {
    let error = document.createElement('h5')
    error.className = 'error'
    error.style.color = 'red'
    error.innerHTML = text
    return error
}

function removeErrors () {
    let errors = form.querySelectorAll('.error')

    for (let i = 0; i < errors.length; i++) {
        errors[i].remove()
    }
}