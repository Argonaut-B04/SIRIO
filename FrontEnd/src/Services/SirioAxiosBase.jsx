export default class SirioAxiosBase {
    static USERNAME_NAME_SESSION_ATTRIBUTE_NAME = "SirioInSession";
    static ROLE_NAME_SESSION_ATTRIBUTE_NAME = "SirioRoleInSession";
    static TOKEN_SESSION_ATTRIBUTE_NAME = "SirioTokenInSession";
    static BASEURL = "http://sirio-api.herokuapp.com/api/v1";

    static formatDate(cell) {
        if (cell == null || cell.length < 3) {
            return "-";
        }
        const date = cell[2];
        var month = cell[1];
        const year = cell[0];

        var monthName = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"];

        month = monthName[month - 1];

        return date + " " + month + " " + year;
    }

    static formatDateFromString(string) {
        if (string) {
            const dateStringinArray = string.split(" ");
            const dateinArray = dateStringinArray[0].split("-");
            return this.formatDate(dateinArray);
        } else {
            return "-"
        }
    }

    static formatDateFromSirioDatePicker(string) {
        const dateinArray = string.split("-");
        return SirioAxiosBase.formatDate(dateinArray);
    }

    static formatDateYear(cell, row) {
        const tanggal = row;
        const tanggalArray = tanggal.split("-");
        return tanggalArray[0];
    }

    static formatDateMonth(cell, row) {
        const tanggal = row;
        const dateStringinArray = tanggal.split(" ");
        const tanggalArray = dateStringinArray.split("-");
        var month = tanggalArray[1];
        var monthName = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"];

        month = monthName[month - 1];

        return month;
    }
}