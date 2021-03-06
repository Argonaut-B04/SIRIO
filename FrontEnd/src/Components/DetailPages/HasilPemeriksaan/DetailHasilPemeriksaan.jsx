import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import { NavLink } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import AuthenticationService from "../../../Services/AuthenticationService";
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";


class DetailHasilPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            hasilPemeriksaan: {},
            dataGeneral: {},
            dataKomponen: [],
            riskScore: 100,
            role: AuthenticationService.getRole(),
            username: AuthenticationService.getUsername(),
            yangDitugaskan: false,
            hapus: false,
            setuju: false,
        };

        this.renderDataHasilPemeriksaan = this.renderDataHasilPemeriksaan.bind(this);
        this.reduceRiskScore = this.reduceRiskScore.bind(this);
        this.setRedirectHapus = this.setRedirectHapus.bind(this);
        this.setRedirectSetuju = this.setRedirectSetuju.bind(this);
    }

    componentDidMount() {
        this.renderDataHasilPemeriksaan();
    }

    setRedirectHapus = () => {
        this.setState({
            hapus: true
        })
    };

    setRedirectSetuju = () => {
        this.setState({
            setuju: true
        })
    };

    renderRedirectHapus = () => {
        if (this.state.hapus) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    renderRedirectSetuju = () => {
        if (this.state.setuju) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    setujuSuccess: true
                }
            }} />
        }
    };

    async renderDataHasilPemeriksaan() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");
        const response = await HasilPemeriksaanService.getHasilPemeriksaan(this.props.location.state.id);

        this.props.changeLoadingBody("Menampilkan data");
        this.setState({
            hasilPemeriksaan: response.data.result,
            dataGeneral: {
                "ID": response.data.result.id,
                "ID Tugas Pemeriksaan": response.data.result.tugasPemeriksaan.id,
                "QA Ditugaskan": response.data.result.tugasPemeriksaan.namaQA,
                "Kantor Cabang": response.data.result.tugasPemeriksaan.namaKantorCabang,
                "Pembuat": response.data.result.namaPembuat,
                "Pemeriksa": this.namaPemeriksaFormatter(response.data.result.namaPemeriksa),
                "Status": this.statusFormatter(response.data.result.namaStatus),
            },
            yangDitugaskan: this.state.username === response.data.result.tugasPemeriksaan.usernameQA,
            dataKomponen: response.data.result.daftarKomponenPemeriksaan.map(komponen => {
                this.reduceRiskScore(komponen.bobotRiskLevel);
                return (
                    {
                        "Komponen Risiko": komponen.risiko.nama,
                        "SOP": this.getSOPButton(komponen.risiko.namaSop, komponen.risiko.linkSop),
                        "Deskripsi": komponen.risiko.deskripsi,
                        "Metodologi": komponen.risiko.metodologi,
                        "Ketentuan Sampel": komponen.risiko.ketentuanSampel ? komponen.risiko.ketentuanSampel : "N/A",
                        "Jumlah Populasi": (komponen.jumlahPopulasi || komponen.jumlahPopulasi === 0) ? komponen.jumlahPopulasi : "Belum Diisi",
                        "Jumlah Sampel": (komponen.jumlahPopulasi || komponen.jumlahPopulasi === 0) ? komponen.jumlahSampel : "Belum Diisi",
                        "Jumlah Sampel Error": (komponen.jumlahPopulasi || komponen.jumlahPopulasi === 0) ? komponen.jumlahSampelError : "Belum Diisi",
                        "Keterangan Sampel": komponen.keteranganSampel ? komponen.keteranganSampel : "Belum Diisi",
                        "Risk Level": komponen.namaRiskLevel ? komponen.namaRiskLevel : "Belum Diisi",
                        "Hasil Temuan": !(komponen.daftarTemuanRisikoTerdaftar.length > 0) ? "-" :
                            <div>
                                {komponen.daftarTemuanRisikoTerdaftar.map((temuanRisiko, i) =>
                                    <p key={i}>- {temuanRisiko.keterangan}</p>
                                )}
                            </div>,
                        "Rekomendasi": !(komponen.daftarRekomendasiTerdaftar.length > 0) ? "-" :
                            <div>
                                {komponen.daftarRekomendasiTerdaftar.map((rekomendasi, i) =>
                                    <p key={i}>- {rekomendasi.keterangan}</p>
                                )}
                            </div>
                    }
                )
            })
        }, this.props.contentFinishLoading())
    }

    reduceRiskScore(deductionPoint) {
        const original = this.state.riskScore;
        this.setState({
            riskScore: (original + deductionPoint)
        })
    }

    namaPemeriksaFormatter(nama) {
        if (nama) {
            return nama
        } else {
            return "-"
        }
    }

    hapus(id) {
        const hasilPemeriksaan = {
            id: id
        };

        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");
        HasilPemeriksaanService.deleteHasilPemeriksaan(hasilPemeriksaan)
            .then(() => this.props.contentFinishLoading(), () => this.props.contentFinishLoading())
            .then(() => this.setRedirectHapus());
    }

    setuju(id) {
        const persetujuan = {
            id: id,
            status: "4",
        };

        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");
        HasilPemeriksaanService.setujuiHasilPemeriksaan(persetujuan)
            .then(() => this.props.contentFinishLoading(), () => this.props.contentFinishLoading())
            .then(() => this.setRedirectSetuju());
    }

    buttonUbah(id, idTugasPemeriksaan, status) {
        if (status === "Draft" || status === "Menunggu Persetujuan" || status === "Ditolak") {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/ubah",
                    state: {
                        id: id,
                        idTugasPemeriksaan: idTugasPemeriksaan
                    }
                }}>
                    <SirioButton
                        purple
                        recommended
                        classes = "mb-5 mx-1"
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
            )
        } else {
            return null
        }
    }

    buttonHapus(id, status) {
        if (status === "Draft" || status === "Menunggu Persetujuan" || status === "Ditolak") {
            return (
                <SirioWarningButton
                    red
                    classes = "mb-5 mx-1"
                    modalTitle="Konfirmasi Penghapusan"
                    modalDesc="Apakah anda yakin untuk menghapus hasil pemeriksaan?"
                    onConfirm={() => this.hapus(id)}
                    customConfirmText="Ya, Hapus"
                    customCancelText="Batal"
                >
                    Hapus
                </SirioWarningButton>
            )
        } else {
            return null
        }
    }

    buttonSetuju(id, status) {
        if (status === "Menunggu Persetujuan") {
            return (
                <SirioConfirmButton
                    purple
                    recommended
                    classes="mb-5 mx-1"
                    modalTitle="Apakah anda yakin untuk menyetujui hasil pemeriksaan?"
                    onConfirm={() => this.setuju(id)}
                    customConfirmText="Ya, Setujui"
                    customCancelText="Batal"
                >
                    Setuju
                </SirioConfirmButton>
            )
        } else {
            return null
        }
    }

    buttonTolak(id, status) {
        if (status === "Menunggu Persetujuan") {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/tolak",
                    state: {
                        id: id
                    }
                }}>
                    <SirioButton
                        purple
                        classes = "mb-5 mx-1"
                    >
                        Tolak
                    </SirioButton>
                </NavLink>
            )
        } else {
            return null
        }
    }

    subButton() {
        switch (this.state.role) {
            case "Super QA Officer Operational Risk":
                if (this.state.hasilPemeriksaan.tugasPemeriksaan) {
                    console.log(this.state.hasilPemeriksaan.tugasPemeriksaan)
                    return (
                        <div>
                            {this.buttonUbah(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.tugasPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonHapus(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonSetuju(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonTolak(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                        </div>
                    );
                } else {
                    return null
                }
            case "dev":
                if (this.state.hasilPemeriksaan.tugasPemeriksaan) {
                    return (
                        <div>
                            {this.buttonUbah(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.tugasPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonHapus(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonSetuju(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonTolak(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                        </div>
                    );
                } else {
                    return null
                }
            case "QA Officer Operational Risk":
                if (this.state.yangDitugaskan && this.state.hasilPemeriksaan.tugasPemeriksaan) {
                    return (
                        <div>
                            {this.buttonUbah(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.tugasPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                            {this.buttonHapus(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                        </div>
                    )
                } else {
                    return null
                }
            case "QA Lead Operational Risk":
                return (
                    <div>
                        {this.buttonSetuju(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                        {this.buttonTolak(this.state.hasilPemeriksaan.id, this.state.hasilPemeriksaan.namaStatus)}
                    </div>
                );
            default:
                return null
        }
    }

    statusFormatter(status) {
        switch (status) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{status}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{status}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{status}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{status}</span>
                );
            default:
                return (status);
        }
    }

    getSOPButton(namaSop, linkSop) {
        return (
            <SirioButton
                purple
                hyperlinkLeft
                onClick={() => window.location.href = linkSop}
            >
                {namaSop}
            </SirioButton>
        )
    }

    render() {
        return (
            <div>
                {this.renderRedirectHapus()}
                {this.renderRedirectSetuju()}
                <SirioDetailPage
                    link="hasil-pemeriksaan"
                    title="Detail Hasil Pemeriksaan"
                    data={this.state.dataGeneral}
                    id='id'
                />
                <SirioDetailPage
                    noBack
                    data={{
                        "Risk Score": this.state.riskScore,
                        "Feedback": this.state.hasilPemeriksaan.feedback ? this.state.hasilPemeriksaan.feedback : "N/A"
                    }}
                    id='id'
                />
                {this.state.dataKomponen.map(komponen =>
                    <SirioDetailPage
                        noBack
                        data={komponen}
                        id='id'
                        key='id'
                    />
                )}
                <div className="w-100 text-right">
                    {this.subButton()}
                </div>
            </div>
        );
    }
}

export default withRouter(DetailHasilPemeriksaan);