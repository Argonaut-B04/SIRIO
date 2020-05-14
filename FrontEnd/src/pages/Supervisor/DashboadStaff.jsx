import React from "react";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import PollingService from "../../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../../Components/Chart/SirioBarChart";
import SirioDashboardBox from "../../Components/Box/SirioDashboardBox";
import DashboardStaffService from '../../Services/DashboardStaffService';
import { Redirect } from 'react-router-dom';

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
            listTemuan: [],
            listRekomendasiOverdue: [],
            listRekomendasiBelumDiimplementasi: [],
            listRekomendasiDiimplementasi: [],
            listMonth: [],
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
                jumlahRekomendasiDiimplementasi: response.data.result.jumlahRekomendasiDiimplementasi,
                listTemuan: response.data.result.listTemuan,
                listRekomendasiOverdue: response.data.result.listRekomendasiOverdue,
                listRekomendasiBelumDiimplementasi: response.data.result.listRekomendasiBelumDiimplementasi,
                listRekomendasiDiimplementasi: response.data.result.listRekomendasiDiimplementasi,
                listMonth: response.data.result.listMonth
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
            labels: this.state.listMonth,
            datasets: [
                {
                    label: 'Jumlah Temuan',
                    backgroundColor: '#7F3F98',
                    borderColor: '#7F3F98',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    hoverBackgroundColor: '#7F3F98',
                    hoverBorderColor: '#7F3F98',
                    data: this.state.listTemuan
                },
                {
                    label: 'Jumlah Rekomendasi Diimplementasi',
                    backgroundColor: '#5DBCD2',
                    borderColor: '#5DBCD2',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    hoverBackgroundColor: '#5DBCD2',
                    hoverBorderColor: '#5DBCD2',
                    data: this.state.listRekomendasiDiimplementasi
                },
                {
                    label: 'Jumlah Rekomendasi Belum Diimplementasi',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(255, 0, 0, 0.85)',
                    borderColor: 'rgba(255, 0, 0, 0.85)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255, 0, 0, 0.85)',
                    hoverBorderColor: 'rgba(255, 0, 0, 0.85)',
                    data: this.state.listRekomendasiBelumDiimplementasi
                },
                {
                    label: 'Jumlah Rekomendasi Overdue',
                    stack: 'Stack 1',
                    backgroundColor: '#F2C94C',
                    borderColor: '#F2C94C',
                    borderWidth: 1,
                    hoverBackgroundColor: '#F2C94C',
                    hoverBorderColor: '#F2C94C',
                    data: this.state.listRekomendasiOverdue
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
                value: this.state.jumlahRekomendasiDiimplementasi + "%"
            },
            {
                title: "Rekomendasi Belum Diimplementasi",
                value: this.state.jumlahRekomendasiBelumDiimplementasi + "%"
            },
            {
                title: "Rekomendasi Overdue",
                value: this.state.jumlahRekomendasiOverdue + "%"
            },
            {
                title: "Temuan",
                value: this.state.jumlahTemuan
            }
        ]
        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                <h1 className="text-left">Dashboard Performa Staff Operational Risk</h1>
                <br></br>
                <div>
                    <SirioBarChart data={data} />
                    <h6 className="text-right pt-3">Data ini hanya data sementara</h6>
                </div>

                <div>
                    <SirioDashboardBox data={boxData} />
                </div>
            </SirioMainLayout>
        )
    }
}

export default withRouter(DashboardStaff);