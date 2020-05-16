import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardService from "../../Services/DashboardService";
import SirioField from "../../Components/Form/SirioFormComponent/SirioField";
import SirioForm from "../../Components/Form/SirioForm";
import AuthenticationService from "../../Services/AuthenticationService";

class DashboardKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
            contentLoading: !PollingService.isConnected(),
            dashboardComponent: {},
            role: AuthenticationService.getRole()
        }
        this.renderDataDashboard = this.renderDataDashboard.bind(this);
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.renderDataDashboard();
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

    async renderDataDashboard() {
        const response = await DashboardService.getDashboard();

        this.setState({
            dashboardComponent: response.data.result
        })
    }

    getBetween() {
        return (
            <>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        // handleChange={this.handleSelectChangeRisiko}
                        classes="p-1"
                        name="kategoriType"
                        value="Semua Kantor Cabang"
                        optionList={
                            [
                                {
                                    label: "Semua Kantor Cabang",
                                    value: "0"
                                },
                                {
                                    label: "Abung Selatan",
                                    value: "1"
                                },
                                {
                                    label: "Adipala",
                                    value: "2"
                                },
                                {
                                    label: "Adiwerna",
                                    value: "2"
                                },
                                {
                                    label: "Air Joman",
                                    value: "2"
                                },
                                {
                                    label: "Air Putih",
                                    value: "2"
                                }
                            ]
                        }
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        // handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="filterKategori"
                        // value={this.state.filterKategori}
                        // optionList={this.state.riskOptionList}
                    />
                </div>
            </>
        )
    }

    getBoxData() {
        const role = this.state.role;
        const qaOfficer = role === "QA Officer Operational Risk" || role === "Super QA Officer Operational Risk";
        const branchManager = role === "Branch Manager";

        if (qaOfficer) {
            return ([
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
                    title: "Jumlah Temuan",
                    value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
                },
                {
                    title: "Jumlah Rekomendasi",
                    value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
                },
            ])
        } else if (branchManager) {
            return ([
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
                    title: "Jumlah Temuan",
                    value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
                },
                {
                    title: "Jumlah Rekomendasi",
                    value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
                },
                {
                    title: "Jumlah Pemeriksaan",
                    value: <p>{this.state.dashboardComponent.jumlahPemeriksaan}</p>
                },
                {
                    title: "Risk Score",
                    value: <p>{this.state.dashboardComponent.riskScore}</p>
                },
            ])
        }
    }

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const data = {
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
                    backgroundColor: '#6FCF97',
                    borderColor: '#6FCF97',
                    hoverBackgroundColor: '#a8e2c0',
                    hoverBorderColor: '#6FCF97',
                    data: this.state.dashboardComponent.jumlahRekomendasiNotImplementedPerBulan
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    backgroundColor: '#F2C94C',
                    borderColor: '#F2C94C',
                    hoverBackgroundColor: '#f7df93',
                    hoverBorderColor: '#F2C94C',
                    data: this.state.dashboardComponent.jumlahRekomendasiOverduePerBulan
                }
            ]
        };

        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                {/* <div>
                    <SirioForm
                        
                        betweenTitleSubtitle={this.getBetween()}
                        inputDefinition={null}
                        onSubmit={null}
                        submitButton={null}
                    />
                </div> */}
                <div>
                    <h3>Dashboard Performa Seluruh Kantor Cabang</h3><br></br>
                    <SirioBarChart data={data} />
                </div>

                <div>
                    <SirioDashboardBox data={this.getBoxData()} />
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardKantorCabang);