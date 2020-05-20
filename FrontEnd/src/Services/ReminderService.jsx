import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class ReminderService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getByIdRekomendasi(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Reminder/getByRekomendasi`, data);
    }

    async submitChanges(idRekomendasi, data) {
        const dataToPass = {
            id: idRekomendasi,
            reminder: data
        }

        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Reminder/konfigurasi', dataToPass)
    }

    async changeTemplate(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Reminder/atur-template-reminder', data)
    }

    async getTemplateByIdReminder(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Reminder/get-template-reminder/' + id)
    }

    async getTemplateGlobal() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + "/Reminder/get-template-reminder-global");
    }
}

export default new ReminderService();