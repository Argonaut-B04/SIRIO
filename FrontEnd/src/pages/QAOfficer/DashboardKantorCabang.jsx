import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardService from "../../Services/DashboardService";

class DashboardKantorCabang extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
            contentLoading: !PollingService.isConnected(),
            dashboardComponent: {}
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

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const data = {
            labels: ['Dapet PR', 'Dapet PR Besok Deadline', 'Besok Kuis', 'Haaa? Hari ini ada kuis?', '"Aku cuma liat kamu sebagai teman"'],
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    backgroundColor: 'rgba(251, 251, 181, 0.6)',
                    borderColor: 'rgba(244, 244, 35, 0.96)',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(252, 252, 7, 0.6)',
                    hoverBorderColor: 'rgba(234, 234, 46, 0.96)',
                    data: [this.state.dashboardComponent.jumlahTemuan]
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    backgroundColor: 'rgba(34, 245, 34, 0.3)',
                    borderColor: 'rgba(46, 234, 46, 0.96)',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(34, 245, 34, 0.96)',
                    hoverBorderColor: 'rgba(46, 234, 46, 0.96)',
                    data: [this.state.dashboardComponent.jumlahRekomendasiImplemented]
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(255,99,132,0.3)',
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255,99,132,0.4)',
                    hoverBorderColor: 'rgba(255,99,132,1)',
                    data: [this.state.dashboardComponent.jumlahRekomendasiNotImplemented]
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(44, 130, 201, 0.4)',
                    borderColor: 'rgba(44, 130, 201, 1)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(44, 130, 201, 0.4)',
                    hoverBorderColor: 'rgba(44, 130, 201, 1)',
                    data: [this.state.dashboardComponent.jumlahRekomendasiOverdue]
                }
            ]
        };

        console.log(this.state.dashboardComponent);
        console.log(this.state.dashboardComponent.jumlahRekomendasi);
        const boxData = [
            {
                title: "Jumlah Temuan",
                value: <p>{this.state.dashboardComponent.jumlahTemuan}</p>
            },
            {
                title: "Jumlah Rekomendasi",
                value: <p>{this.state.dashboardComponent.jumlahRekomendasi}</p>
            },
            {
                title: "Rekomendasi Diimplementasi",
                value: <p>{this.state.dashboardComponent.persenRekomendasiImplemented}%</p>
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: <p>{this.state.dashboardComponent.persenRekomendasiNotImplemented}%</p>
            },
            {
                title: "Rekomendasi Overdue",
                value: <p>{this.state.dashboardComponent.persenRekomendasiOverdue}%</p>
            }
        ]
        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                <div>
                    <h3>Dashboard Performa Seluruh Kantor Cabang</h3><br></br>
                    <SirioBarChart data={data} />
                </div>

                <div>
                    <SirioDashboardBox data={boxData} />
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardKantorCabang);