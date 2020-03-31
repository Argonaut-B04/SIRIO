export default class SirioAxiosBase {
    static USERNAME_NAME_SESSION_ATTRIBUTE_NAME = "SirioInSession";
    static ROLE_NAME_SESSION_ATTRIBUTE_NAME = "SirioRoleInSession";
    static TOKEN_SESSION_ATTRIBUTE_NAME = "SirioTokenInSession";
    static BASEURL = "http://localhost:8080/api/v1";

    static formatDate(cell) {
        const date = cell[2];
        var month = cell[1];
        const year = cell[0]

        var monthName = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"];

        month = monthName[month - 1];

        return date + " " + month + " " + year;
    }

    static formatDateFromString(string) {
        const dateStringinArray = string.split(" ");
        const dateinArray = dateStringinArray[0].split("-");
        return this.formatDate(dateinArray);
    }

    static formatDateFromSirioDatePicker(string) {
        const dateinArray = string.split("-");
        return this.formatDate(dateinArray);
    }
}