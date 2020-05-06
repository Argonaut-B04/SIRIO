import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardStaffService from '../../Services/DashboardStaffService';
import { NavLink, Redirect } from 'react-router-dom';

/**
 * Controller untuk menampilkan halaman utama
 */
class DashboardStaff extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            jumlahRekomendasi: "",
            jumlahTemuan: "",
            jumlahRekomendasiOverdue: "",
            jumlahRekomendasiBelumDiimplementasi: "",
            jumlahRekomendasiDiimplementasi: "",
            preloader: true,
            contentLoading: !PollingService.isConnected()
        }
        this.renderRows = this.renderRows.bind(this);
    }

    async renderRows() {
        DashboardStaffService.getAllData().then(response => {
            this.setState({
                jumlahRekomendasi: response.data.result.jumlahRekomendasi,
                jumlahTemuan: response.data.result.jumlahTemuan,
                jumlahRekomendasiOverdue: response.data.result.jumlahRekomendasiOverdue,
                jumlahRekomendasiBelumDiimplementasi: response.data.result.jumlahRekomendasiBelumDiimplementasi,
                jumlahRekomendasiDiimplementasi: response.data.result.jumlahRekomendasiDiimplementasi
            })
        })
        .catch(error => {
            if (error.response.data.status === 401) {
                this.setState({
                    redirector: <Redirect to={{
                        pathname: "/401",
                        state: {
                            detail: error.response.data.message,
                        }
                    }} />
                })
            }
        })
        ;
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.renderRows();
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

    render() {
        const { preloader, contentLoading, loadingBody } = this.state;
        const data = {
            labels: ['Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni'],
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    backgroundColor: 'rgba(251, 251, 181, 0.6)',
                    borderColor: 'rgba(244, 244, 35, 0.96)',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(252, 252, 7, 0.6)',
                    hoverBorderColor: 'rgba(234, 234, 46, 0.96)',
                    data: [this.state.jumlahTemuan,12,14,14,43,32]
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    backgroundColor: 'rgba(34, 245, 34, 0.3)',
                    borderColor: 'rgba(46, 234, 46, 0.96)',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(34, 245, 34, 0.96)',
                    hoverBorderColor: 'rgba(46, 234, 46, 0.96)',
                    data: [this.state.jumlahRekomendasiDiimplementasi, 95, 90, 40, 10,2]
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(255,99,132,0.2)',
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255,99,132,0.4)',
                    hoverBorderColor: 'rgba(255,99,132,1)',
                    data: [this.state.jumlahRekomendasiBelumDiimplementasi, 55, 60, 110, 140,10]
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(254,99,132,0.2)',
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255,99,132,0.4)',
                    hoverBorderColor: 'rgba(255,99,132,1)',
                    data: [this.state.jumlahRekomendasiOverdue, 55, 60, 110, 140,40]
                }
            ]
        };

        const boxData = [
            {
                title: "Rekomendasi",
                value: this.state.jumlahRekomendasi
            },
            {
                title: "Rekomendasi Diimplementasi",
                value: this.state.jumlahRekomendasiDiimplementasi
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: this.state.jumlahRekomendasiBelumDiimplementasi
            },
            {
                title: "Rekomendasi Overdue",
                value: this.state.jumlahRekomendasiOverdue
            },
            {
                title: "Temuan",
                value: this.state.jumlahTemuan
            }
        ]
        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                <h1 className="text-center">Welcome to SIRIO</h1>
                <div>
                    <h3 className="text-center">Perbandingan Reaksi Saat Mendengar / Mengetahui (faktor sumbu x)</h3>
                    <SirioBarChart data={data} />
                    <h6 className="text-right pt-3">Data diambil dari sumber yang tidak terpercaya sehingga dapat dipastikan tidak benar</h6>
                </div>

                <div>
                    <SirioDashboardBox data={boxData} />
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardStaff);