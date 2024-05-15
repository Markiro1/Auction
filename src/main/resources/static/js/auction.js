const authToken = getCookie('token');
const tokenData = parseJwt(authToken);

document.getElementById("userBidInput").addEventListener("input", function () {
    const button = document.getElementById("submitBidButton");
    const currentValue = parseInt(this.value);
    const minInput = parseInt(document.getElementById("minInput").value);
    const maxInput = parseInt(document.getElementById("maxInput").value);

    if (isNaN(currentValue) || currentValue < minInput || currentValue > maxInput) {
        button.disabled = true;
    } else {
        button.disabled = false;
    }
})


const startTimeStr = document.getElementById('startTime').textContent;
const endTimeStr = document.getElementById('endTime').textContent;
const startTime = new Date(Date.parse(startTimeStr));
const endTime = new Date(Date.parse(endTimeStr));

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error("Error parsing JWT", error);
        return null;
    }
}

function getUserId() {
    return tokenData.userId;
}