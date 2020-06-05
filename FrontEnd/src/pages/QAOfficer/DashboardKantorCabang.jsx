import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import { Redirect } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardKantorService from "../../Services/DashboardKantorService";
import KantorCabangService from "../../Services/KantorCabangService";
import SirioField from "../../Components/Form/SirioFormComponent/SirioField";
import SirioButton from '../../Components/Button/SirioButton';
import SirioComponentHeader from "../../Components/Header/SirioComponentHeader";
import AuthenticationService from "../../Services/AuthenticationService";
import moment from 'moment';
import 'moment/locale/id';
import { Row, Col } from 'react-bootstrap';

class DashboardKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
            contentLoading: !PollingService.isConnected(),
            dashboardComponent: {},
            kantorCabangList: [],
            namaKantorList: [],
            areaKantorList: [],
            regionalKantorList: [],
            namaKantor: "",
            areaKantor: "",
            regionalKantor: "",
            tanggalPertama: "",
            tanggalKedua: "",
            namaChanged: false,
            role: AuthenticationService.getRole()
        }
        this.renderData = this.renderData.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeNama = this.handleSelectChangeNama.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleReset = this.handleReset.bind(this);
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.renderData();
        if (!PollingService.isConnected()) {
            this.setState({
                preloader: false,
                contentLoading: true,
                loadingBody: "Mencoba untuk menghubungi server, harap tunggu sebentar"
            })

            PollingService.pollServer()
                .then(response => {
                    this.setState({
                        loadingBody: response.data.result
                    })

                    PollingService.connected();

                    setTimeout(function () { // Memberikan jeda waktu 0.5 detik
                        this.setState({
                            contentLoading: false
                        })
                    }.bind(this), 500)
                })
                .catch(error => {
                    this.setState({
                        loadingBody: "Gagal menghubungi server, harap refresh halaman ini"
                    })
                })
        } else {
            this.setState({
                preloader: false,
            })
        }
    }

    // Fungsi untuk menghentikan tampilan loader konten
    contentFinishLoading() {
        setTimeout(function () { // Memberikan jeda waktu 0.5 detik
            this.setState({
                contentLoading: false
            })
        }.bind(this), 500)
    }

    // Fungsi untuk menampilkan loader konten
    contentStartLoading() {
        this.setState({
            contentLoading: true
        })
    }

    // Fungsi untuk mengubah teks loader konten
    changeLoadingBody(body) {
        this.setState({
            loadingBody: body
        })
    }

    async renderData() {
        this.contentStartLoading();
        this.changeLoadingBody("Mengambil data dari server");
        const responseDashboardComp = await DashboardKantorService.getAllComponent();
        const dashboardComponent = responseDashboardComp.data.result;

        const responseKantorCabang = await KantorCabangService.getKantorCabangList();
        const kantorCabangList = responseKantorCabang.data.result;

        const namaKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.namaKantor,
                    value: kc.namaKantor
                }
            )
        });
        const areaKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.area,
                    value: kc.area
                }
            )
        });
        const regionalKantorCabang = responseKantorCabang.data.result.map(kc => {
            return (
                {
                    label: kc.regional,
                    value: kc.regional
                }
            )
        });

        this.setState({
            dashboardComponent: dashboardComponent,
            kantorCabangList: kantorCabangList,
            namaKantorList: namaKantorCabang,
            areaKantorList: areaKantorCabang,
            regionalKantorList: regionalKantorCabang,
        }, this.contentFinishLoading());
    }

    getUnique(arr, index) {
        const unique = arr
            .filter(arr => arr[index] !== "")
            .map(e => e[index])
            .map((e, i, final) => final.indexOf(e) === i && i)
            .filter(e => arr[e]).map(e => arr[e]); 
        return unique;
    }

    submitableFirst() {
        if (this.state.tanggalPertama !== "") {
            return this.state.tanggalKedua !== "";
        } else {
            return this.state.namaKantor !== "" 
            || this.state.areaKantor !== "" 
            || this.state.regionalKantor !== "" 
            || this.state.tanggalKedua !== ""
        }
    }

    submitableSecond() {
        if (this.state.tanggalPertama !== "") {
            return this.state.tanggalKedua !== "";
        } 
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value
            }
        )
    }

    handleSelectChangeNama(name, event) {
        const listKC = this.state.kantorCabangList;
        for (var i in listKC) {
            if (event.value === listKC[i].namaKantor) {
                this.setState(
                    {
                        [name]
                            : event.value,
                        areaNamaKantor: listKC[i].area,
                        regionalNamaKantor: listKC[i].regional,
                        namaChanged: true
                    }
                )
            }
        }
    }

    handleChange(event) {
        const { name, value } = event.target;
        if (name === "tanggalPertama") {
            this.setState({
                tanggalKedua: ""
            })
        }
        this.setState(
            {
                [name]
                    : value
            }
        )
    }

    handleSubmit(event) {
        this.contentStartLoading();
        this.changeLoadingBody("Mengambil data dari server");

        event.preventDefault();
        const filter = {
            namaKantor: this.state.namaKantor,
            areaKantor: this.state.areaKantor,
            regionalKantor: this.state.regionalKantor,
            tanggalPertama: this.state.tanggalPertama,
            tanggalKedua: this.state.tanggalKedua
        };
        DashboardKantorService.getAllComponentByFilter(filter)
            .then((response) => {
                if (this.state.namaKantor !== "" && this.state.tanggalPertama !== "" && this.state.tanggalKedua !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterNamaTanggal: true
                    }, this.contentFinishLoading());
                } else if (this.state.tanggalPertama !== "" && this.state.tanggalKedua !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterTanggal: true
                    }, this.contentFinishLoading());
                } else if (this.state.namaKantor !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterNama: true
                    }, this.contentFinishLoading());
                } else if (this.state.areaKantor !== "" && this.state.regionalKantor !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterAreaRegional: true
                    }, this.contentFinishLoading());
                } else if (this.state.areaKantor !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterArea: true
                    }, this.contentFinishLoading());
                } else if (this.state.regionalKantor !== "") {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterRegional: true
                    }, this.contentFinishLoading());
                } else {
                    this.setState({
                        dashboardComponent: response.data.result,
                        filterNamaTanggal: false,
                        filterTanggal: false,
                        filterNama: false,
                        filterAreaRegional: false,
                        filterArea: false,
                        filterRegional: false
                    }, this.contentFinishLoading());
                }
            })
    }

    handleReset(event) {
        this.contentStartLoading();
        this.changeLoadingBody("Mengambil data dari server");

        event.preventDefault();
        DashboardKantorService.getAllComponent()
            .then((response) =>
            this.setState({
                dashboardComponent: response.data.result,
                namaKantor: "",
                areaKantor: "",
                regionalKantor: "",
                tanggalPertama: "",
                tanggalKedua: "",
                namaChanged: false,
                filterNamaTanggal: false,
                filterTanggal: false,
                filterNama: false,
                filterAreaRegional: false,
                filterArea: false,
                filterRegional: false
            }, this.contentFinishLoading()));
    }

    getBetweenFirst() {
        var max = moment(this.state.tanggalPertama).add(2, 'y')
        max = max.subtract(1, 'd')
        return (
            <Row>
                <Col lg={4} xs={12}>
                    <div>
                        <SirioField
                            type="select"
                            handleChange={this.handleSelectChangeNama}
                            classes="p-1"
                            name="namaKantor"
                            value={this.state.namaKantor}
                            optionList={this.state.namaKantorList}
                            placeholder="Semua Kantor Cabang"
                            customClass="w-100"
                        />
                    </div>
                    <div>
                        {this.state.namaChanged ? <h5>Area: {this.state.areaNamaKantor}</h5> :
                        <SirioField
                            type="select"
                            handleChange={this.handleSelectChange}
                            classes="p-1"
                            name="areaKantor"
                            value={this.state.areaKantor}
                            optionList={this.getUnique(this.state.areaKantorList, 'label')}
                            placeholder="Semua Area"
                            customClass="w-100"
                        />
                        }
                    </div>
                    <div>
                        {this.state.namaChanged ? <h5>Regional: {this.state.regionalNamaKantor}</h5> :
                        <SirioField
                            type="select"
                            handleChange={this.handleSelectChange}
                            classes="p-1"
                            name="regionalKantor"
                            value={this.state.regionalKantor}
                            optionList={this.getUnique(this.state.regionalKantorList, 'label')}
                            placeholder="Semua Regional"
                            customClass="w-100"
                        />
                        }
                    </div>
                </Col>
                <Col lg={6} xs={12}>
                    <div className="pl-0">
                        <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            classes="p-1"
                            name="tanggalPertama"
                            value={this.state.tanggalPertama}
                            label="Tanggal Awal: "
                        />
                    </div>
                    <div className="pl-0">
                        <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            disabled={this.state.tanggalPertama === ""}
                            classes="p-1"
                            min={this.state.tanggalPertama}
                            max={max}
                            required={this.state.tanggalPertama !== ""}
                            name="tanggalKedua"
                            value={this.state.tanggalKedua}
                            label="Tanggal Akhir: "
                        />
                    </div>
                    <div style={{marginTop: "20px"}}>
                        {this.getButtonFirst()}
                        {this.getButtonThird()}
                    </div>
                </Col>
            </Row>
        )
    }

    getBetweenSecond() {
        var max = moment(this.state.tanggalPertama).add(2, 'y')
        max = max.subtract(1, 'd')
        return (
            <>
                <div style={{marginTop: "30px"}}>
                    <div className="col-md-6 pl-0">
                        <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            classes="p-1"
                            name="tanggalPertama"
                            value={this.state.tanggalPertama}
                            label="Tanggal Awal: "
                        />
                    </div>
                    <div className="col-md-6 pl-0">
                        <SirioField
                            type="date"
                            handleChange={this.handleChange}
                            disabled={this.state.tanggalPertama === ""}
                            classes="p-1"
                            min={this.state.tanggalPertama}
                            max={max}
                            required={this.state.tanggalPertama !== ""}
                            name="tanggalKedua"
                            value={this.state.tanggalKedua}
                            label="Tanggal Akhir: "
                        />
                    </div>
                </div>
            </>
        )
    }

    getButtonFirst() {
        var tombolCari =
        <SirioButton
            purple
            disabled
            classes="mx-1"
        >
            Cari
        </SirioButton>
        if (this.submitableFirst()) {
            tombolCari = 
            <SirioButton
                purple
                recommended
                classes="mx-1"
                onClick={(event) => this.handleSubmit(event)}
            >
                Cari
            </SirioButton>
        }
        return tombolCari;
    }

    getButtonSecond() {
        var tombolCari =
        <SirioButton
            purple
            disabled
            classes="mx-2"
        >
            Cari
        </SirioButton>
        if (this.submitableSecond()) {
            tombolCari = 
            <SirioButton
                purple
                recommended
                classes="mx-2"
                onClick={(event) => this.handleSubmit(event)}
            >
                Cari
            </SirioButton>
        }
        return tombolCari;
    }

    getButtonThird() {
        var tombolAtur =
        <SirioButton
            purple
            disabled
            classes="mx-1"
            // tooltip="Tekan untuk kembali ke tampilan awal"
        >
            Atur Ulang
        </SirioButton>
        if (this.state.tanggalPertama !== "" || this.state.namaKantor !== "" 
            || this.state.areaKantor !== "" || this.state.regionalKantor !== "") {
            tombolAtur = 
            <SirioButton
                purple
                classes="mx-1"
                // tooltip="Tekan untuk kembali ke tampilan awal"
                onClick={(event) => this.handleReset(event)}
            >
                Atur Ulang
            </SirioButton>
        }
        return tombolAtur;
    }

    getButtonFourth() {
        var tombolAtur =
        <SirioButton
            purple
            disabled
            classes="mx-1"
            tooltip="Tekan untuk kembali ke tampilan awal"
        >
            Atur Ulang
        </SirioButton>
        if (this.state.tanggalPertama !== "") {
            tombolAtur = 
            <SirioButton
                purple
                classes="mx-1"
                tooltip="Tekan untuk kembali ke tampilan awal"
                onClick={(event) => this.handleReset(event)}
            >
                Atur Ulang
            </SirioButton>
        }
        return tombolAtur;
    }

    getChart() {
        return({
            labels: this.state.dashboardComponent.daftarBulan,
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    backgroundColor: '#7F3F98',
                    borderColor: '#7F3F98',
                    hoverBackgroundColor: '#a665bf',
                    hoverBorderColor: '#7F3F98',
                    data: this.state.dashboardComponent.jumlahTemuanPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#5DBCD2',
                    borderColor: '#5DBCD2',
                    hoverBackgroundColor: '#99d5e3',
                    hoverBorderColor: '#5DBCD2',
                    data: this.state.dashboardComponent.jumlahRekomendasiImplementedPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#F2C94C',
                    borderColor: '#F2C94C',
                    hoverBackgroundColor: '#F2C94C',
                    hoverBorderColor: '#F2C94C',
                    data: this.state.dashboardComponent.jumlahRekomendasiNotImplementedPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: 'rgba(255, 0, 0, 0.85)',
                    borderColor: 'rgba(255, 0, 0, 0.85)',
                    hoverBackgroundColor: 'rgba(255, 0, 0, 0.85)',
                    hoverBorderColor: 'rgba(255, 0, 0, 0.85)',
                    data: this.state.dashboardComponent.jumlahRekomendasiOverduePerBulan
                }
            ]
        })
    }

    getBoxFirst() {
        return([
            {
                title: "Rekomendasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
            },
            {
                title: "Rekomendasi Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiImplemented}%</p>
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiNotImplemented}%</p>
            },
            {
                title: "Rekomendasi Overdue",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiOverdue}%</p>
            },
            {
                title: "Temuan",
                value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
            }
        ])
    }

    getBoxSecond() {
        return([
            {
                title: "Rekomendasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
            },
            {
                title: "Rekomendasi Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiImplemented}%</p>
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiNotImplemented}%</p>
            },
            {
                title: "Rekomendasi Overdue",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasiOverdue}%</p>
            },
            {
                title: "Temuan",
                value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
            },
            {
                title: "Pemeriksaan",
                value: <p>{this.state.dashboardComponent.jumlahPemeriksaan}</p>
            },
            {
                title: "Risk Score",
                value: <p>{this.state.dashboardComponent.riskScore}</p>
            }
        ])
    }

    getTitle() {
        var title = "Dashboard Performa Kantor Cabang";
        if (this.state.filterNama) {
            title = "Dashboard Performa Kantor Cabang " + this.state.namaKantor
        } else if (this.state.filterAreaRegional) {
            title = "Dashboard Performa Kantor Cabang " + this.state.areaKantor + " - " + this.state.regionalKantor
        } else if (this.state.filterArea) {
            title = "Dashboard Performa Kantor Cabang " + this.state.areaKantor
        } else if (this.state.filterRegional) {
            title = "Dashboard Performa Kantor Cabang " + this.state.regionalKantor
        } else if (this.state.filterTanggal) {
            title = "Dashboard Performa Kantor Cabang " + this.tanggalFormatter(this.state.tanggalPertama) + " s/d " + this.tanggalFormatter(this.state.tanggalKedua)
        } else if (this.state.filterNamaTanggal) {
            title = "Dashboard Performa Kantor Cabang " + this.state.namaKantor + " - " + this.state.tanggalPertama + " s/d " + this.state.tanggalKedua
        } 
        return title;
    }

    tanggalFormatter(tanggal) {
        return moment(tanggal).locale('id').format("D MMMM YYYY");
    }

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const role = this.state.role;
        const qaOfficer = role === "QA Officer Operational Risk" || role === "Super QA Officer Operational Risk";
        const branchManager = role === "Branch Manager";
        const rekomendasi = this.state.dashboardComponent.jumlahRekomendasi;
        const temuan = this.state.dashboardComponent.jumlahTemuan;

        if (role === "Manajer Operational Risk"
            || role === "QA Lead Operational Risk"
            || role === "Supervisor"
            || role === "Administrator") {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: "Not Authorized",
                        code: "401"
                    }
                }} />
            )
        } 

        if (qaOfficer) {
            if ((rekomendasi === 0 && temuan === 0) || (rekomendasi === "" && temuan === "")) {
                return (
                    <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                        <div>
                            <SirioComponentHeader
                                title={this.getTitle()}
                            />
                            {this.getBetweenFirst()}
                        </div>
                        <div className="text-center mt-5">
                            <h3>Data tidak ditemukan!</h3>
                        </div>
                    </SirioMainLayout>
                )
            } else {
                return (
                    <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                        <div>
                            <SirioComponentHeader
                                title={this.getTitle()}
                            />
                            {this.getBetweenFirst()}
                        </div>
                        <div style={{marginTop: "50px"}}>
                            <h4 className="text-center mt-3 pt-3">Histori Data Temuan dan Rekomendasi untuk Tugas Pemeriksaan pada Suatu Bulan</h4>
                            <br></br>
                            <SirioBarChart data={this.getChart()} />
                        </div>
                        <div>
                            {this.state.filterNama ? 
                                <SirioDashboardBox data={this.getBoxSecond()} /> :
                                <SirioDashboardBox data={this.getBoxFirst()} />
                            }
                        </div>
                    </SirioMainLayout>
                )
            }
        } else if (branchManager) {
            if ((rekomendasi === 0 && temuan === 0) || (rekomendasi === "" && temuan === "")) {
                return (
                    <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                        <div>
                            <SirioComponentHeader
                                title="Dashboard Performa Kantor Cabang"
                                betweenTitleSubtitle={this.getBetweenSecond()}
                            />
                        </div>
                        <div className="col-md-6 pl-0 mt-3">
                            {this.getButtonSecond()}
                            {this.getButtonFourth()}
                        </div>
                        <div className="text-center mt-5">
                            <h3>Data tidak ditemukan!</h3>
                        </div>
                    </SirioMainLayout>
                )
            } else {
                return (
                    <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                        <div>
                            <SirioComponentHeader
                                title="Dashboard Performa Kantor Cabang"
                                betweenTitleSubtitle={this.getBetweenSecond()}
                            />
                        </div>
                        <div className="col-md-6 pl-0 mt-3">
                            {this.getButtonSecond()}
                            {this.getButtonFourth()}
                        </div>
                        <div style={{marginTop: "50px"}}>
                            <h4 className="text-center mt-3 pt-3">Histori Data Temuan dan Rekomendasi untuk Tugas Pemeriksaan pada Suatu Bulan</h4>
                            <br></br>
                            <SirioBarChart data={this.getChart()} />
                        </div>
                        <div>
                            <SirioDashboardBox data={this.getBoxSecond()} />
                        </div>
                    </SirioMainLayout>
                )
            }
        }
    }
}

export default withRouter(DashboardKantorCabang);