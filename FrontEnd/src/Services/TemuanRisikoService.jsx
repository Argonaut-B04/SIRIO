import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class TemuanRisikoService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getHistoriTemuanRisikoKantorCabang(idTugasPemeriksaan) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/TemuanRisiko/` + idTugasPemeriksaan);
    }
}

export default new TemuanRisikoService();