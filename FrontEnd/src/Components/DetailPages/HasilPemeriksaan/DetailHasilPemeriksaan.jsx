import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import AuthenticationService from "../../../Services/AuthenticationService";


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
            yangDitugaskan: false
        }
            
        this.renderDataHasilPemeriksaan = this.renderDataHasilPemeriksaan.bind(this);
        this.reduceRiskScore = this.reduceRiskScore.bind(this);
    }

    componentDidMount() {
        this.renderDataHasilPemeriksaan();
    }

    async renderDataHasilPemeriksaan() {
        const response = await HasilPemeriksaanService.getHasilPemeriksaan(this.props.location.state.id);

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
                        "Jumlah Sampel": komponen.jumlahSampel,
                        "Keterangan Sampel": komponen.keteranganSampel,
                        "Risk Level": komponen.namaRiskLevel,
                        "Hasil Temuan": !(komponen.daftarTemuanRisikoTerdaftar.length > 0) ? "-" :
                            <div>
                                {komponen.daftarTemuanRisikoTerdaftar.map(temuanRisiko =>
                                    <p>- {temuanRisiko.keterangan}</p>
                                )}
                            </div>,
                        "Rekomendasi": !(komponen.daftarRekomendasiTerdaftar.length > 0) ? "-" :
                            <div>
                                {komponen.daftarRekomendasiTerdaftar.map(rekomendasi =>
                                    <p>- {rekomendasi.keterangan}</p>
                                )}
                            </div>
                    }
                )
            })
        })
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

    buttonUbah(id) {
        return (
            <NavLink to={{
                pathname: "/hasil-pemeriksaan/ubah",
                state: {
                    id: id
                }
            }}>
                <SirioButton
                    purple
                >
                    Ubah
                </SirioButton>
            </NavLink>
        )
    }

    buttonHapus(id) {
        return (
            <NavLink to={{
                pathname: "/hasil-pemeriksaan/hapus",
                state: {
                    id: id
                }
            }}>
                <SirioButton
                    purple
                >
                    Hapus
                </SirioButton>
            </NavLink>
        )
    }

    buttonSetuju =
        <SirioConfirmButton
            purple
            classes="m-1"
            modalTitle="Apakah anda yakin untuk menyetujui hasil pemeriksaan?"
            onConfirm={null}
            customConfirmText="Ya, Setujui"
            customCancelText="Batal"
            closeOnConfirm
        >
            Setuju
        </SirioConfirmButton>

    buttonTolak(id) {
        return (
            <NavLink to={{
                pathname: "/hasil-pemeriksaan/tolak",
                state: {
                    id: id
                }
            }}>
                <SirioButton
                    purple
                >
                    Tolak
                </SirioButton>
            </NavLink>
        )
    }

    subButton() {
        switch (this.state.role) {
            case "Super QA Officer Operational Risk":
                return (
                    <div>
                        {this.buttonUbah(this.state.hasilPemeriksaan.id)}
                        {this.buttonHapus(this.state.hasilPemeriksaan.id)}
                        {this.buttonSetuju}
                        {this.buttonTolak(this.state.hasilPemeriksaan.id)}
                    </div>
                )
            case "admin":
                return (
                    <div>
                        {this.buttonUbah(this.state.hasilPemeriksaan.id)}
                        {this.buttonHapus(this.state.hasilPemeriksaan.id)}
                        {this.buttonSetuju}
                        {this.buttonTolak(this.state.hasilPemeriksaan.id)}
                    </div>
                )
            case "QA Officer Operational Risk":
                if (this.state.yangDitugaskan) {
                    return (
                        <div>
                            {this.buttonUbah(this.state.hasilPemeriksaan.id)}
                            {this.buttonHapus(this.state.hasilPemeriksaan.id)}
                        </div>
                    )
                } else {
                    return null
                }
            case "QA Lead Operational Risk":
                return (
                    <div>
                        {this.buttonSetuju}
                        {this.buttonTolak(this.state.hasilPemeriksaan.id)}
                    </div>
                )
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
                <SirioDetailPage
                    title="Detail Hasil Pemeriksaan"
                    data={this.state.dataGeneral}
                    id='id'
                />
                <SirioDetailPage
                    data={{"Risk Score": this.state.riskScore}}
                    id='id'
                />
                {this.state.dataKomponen.map(komponen =>
                    <SirioDetailPage
                        data={komponen}
                        id='id'
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