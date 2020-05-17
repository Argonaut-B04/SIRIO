import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class DashboardService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllComponent(areaKantor) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Dashboard/getAll`, 
            {
                namaKantor: "namaKantor",
                areaKantor: "areaKantor",
                regionalKantor: "regionalKantor"
            }
        );
    }

}

export default new DashboardService();