import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";
import PollingService from "../Services/PollingService";
import { withRouter } from "react-router-dom";
import SirioBarChart from "../Components/Chart/SirioBarChart";
<<<<<<< HEAD
import SirioDashboardBox from "../Components/Box/SirioDashboardBox";
=======
>>>>>>> 562e14a52eed03052f3fea637d77232c419a3bad

/**
 * Controller untuk menampilkan halaman utama
 */
class MainPage extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
            contentLoading: !PollingService.isConnected()
        }
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
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
            labels: ['Dapet PR', 'Dapet PR Besok Deadline', 'Besok Kuis', 'Haaa? Hari ini ada kuis?', '"Aku cuma liat kamu sebagai teman"'],
            datasets: [
                {
                    label: 'Total Responden',
                    backgroundColor: 'rgba(251, 251, 181, 0.6)',
                    borderColor: 'rgba(244, 244, 35, 0.96)',
                    stack: 'Stack 0',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(252, 252, 7, 0.6)',
                    hoverBorderColor: 'rgba(234, 234, 46, 0.96)',
                    data: [150, 150, 150, 150, 150]
                },
                {
                    label: 'Masih ada jalan keluar',
                    backgroundColor: 'rgba(34, 245, 34, 0.3)',
                    borderColor: 'rgba(46, 234, 46, 0.96)',
                    stack: 'Stack 1',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(34, 245, 34, 0.96)',
                    hoverBorderColor: 'rgba(46, 234, 46, 0.96)',
                    data: [125, 95, 90, 40, 10]
                },
                {
                    label: 'Hilang harapan',
                    stack: 'Stack 1',
                    backgroundColor: 'rgba(255,99,132,0.2)',
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 1,
                    hoverBackgroundColor: 'rgba(255,99,132,0.4)',
                    hoverBorderColor: 'rgba(255,99,132,1)',
                    data: [25, 55, 60, 110, 140]
                }
            ]
        };

        const boxData = [
            {
                title: "Taylor Swift",
                value: 22
            },
            {
                title: "6 Digit yang mungkin Cloud tau",
                value: 177013
            },
            {
                title: "Angka Meme di Facebook",
                value: 69420
            },
            {
                title: "Jumlah anggota kelompok",
                value: 5
            },
            {
                title: "Angka di peer review",
                value: 4
            },
            {
                title: "Aku tidak tau ini angka apa",
                value: 9995
            },
            {
                title: "ini supaya box nya ada 7",
                value: 10020
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

export default withRouter(MainPage);