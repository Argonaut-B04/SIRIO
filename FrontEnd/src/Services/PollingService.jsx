import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';

class PollingService {

    constructor() {
        this.axiosInstance = axios.create();
    }

    async pollServer() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/poll');
    }

    connected() {
        sessionStorage.setItem("LoggedSirioServer", new Date());
    }

    addHour(date, hour) {
        var result = new Date(date);
        result.setHours(result.getHours() + hour);
        return result;
    }

    isConnected() {
        var date = sessionStorage.getItem("LoggedSirioServer");
        if (typeof date === 'undefined') {
            return false;
        }
        date = new Date(date);
        date = this.addHour(date, 1);
        return (date > new Date())
    }
}

export default new PollingService();