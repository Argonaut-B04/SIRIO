import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";
import PollingService from "../Services/PollingService";
import { withRouter } from "react-router-dom";

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
        return (
            <SirioMainLayout preloader={preloader} contentLoading={contentLoading} loadingBody={loadingBody} active={!contentLoading}>
                <h3>Welcome to SIRIO</h3>
            </SirioMainLayout>
        )
    }
}

export default withRouter(MainPage);