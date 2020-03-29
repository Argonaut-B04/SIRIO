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
            idRekomendasi: idRekomendasi,
            daftarReminder: data
        }

        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Reminder/konfigurasi', dataToPass)
    }
}

export default new ReminderService();