let form = document.querySelector('.registration')
let btn = form.querySelector('.btn');
let fields = form.querySelectorAll('.field')
let names = form.querySelectorAll('.name')
let email = form.querySelector('.email')
let password = form.querySelector('.password')
let mobile = form.querySelector('.mobile')
let passport = form.querySelector('.passport')

form.addEventListener('submit', function (event) {
    removeErrors()

    if (checkFieldsPresence()) {
        event.preventDefault()
        return
    }

    for (let i = 0; i < names.length; i++) {
        if (validateNames(names[i])) {
            event.preventDefault()
        }
    }

    if (validateEmail() || validatePassword() || validateMobileNumber() || validatePassport()) {
        event.preventDefault()
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

function validateNames(name) {
    let flag = false
    let regex = /^[A-Za-z]{1,30}$/;
    if (name.value.match(regex) == null) {
        let error = generateError('Please write info in Latin letters')
        name.parentElement.insertBefore(error, name)
        flag = true
    }
    return flag
}

function validateEmail() {
    let regex = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/;
    if (email.value.match(regex) == null) {
        let error = generateError("Check email, entered email doesn't exist")
        email.parentElement.insertBefore(error, email)
        return true
    }
    return false
}

function validatePassword() {
    let regex = /^[A-Za-z0-9]{8,20}$/;
    if (password.value.match(regex) == null) {
        let error = generateError("The password must contain more than 8 and less than 20 characters of the Latin alphabet and numbers")
        password.parentElement.insertBefore(error, password)
        return true
    }
    return false
}

function validateMobileNumber() {
    let regex = /^[0-9+]{4,15}$/;
    if (mobile.value.match(regex) == null) {
        let error = generateError("Check mobile number, entered number doesn't exist")
        mobile.parentElement.insertBefore(error, mobile)
        return true
    }
    return false
}

function validatePassport() {
    let regex = /^[A-Za-z0-9]{6,20}$/;
    if (passport.value.match(regex) == null) {
        let error = generateError("Check passport number, entered number doesn't exist")
        passport.parentElement.insertBefore(error, passport)
        return true
    }
    return false
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