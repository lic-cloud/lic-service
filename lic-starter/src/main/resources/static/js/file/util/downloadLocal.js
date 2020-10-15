function downloadLocalHostServer(url) {
    let   link = document.createElement('a');
    link.setAttribute("download", "");
    link.href = url;
    link.click();
}