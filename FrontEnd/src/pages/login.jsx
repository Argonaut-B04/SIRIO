import React from "react";
import SirioSideNav from "../Components/SideNav/SirioSideNav";
import LoginForm from "../Components/Form/LoginForm";
import LoadingOverlay from 'react-loading-overlay';
import LogoTag from "../Components/LogoTag/LogoTag";
import { Ripple } from 'react-preloaders';

/**
 * Controller untuk menampilkan halaman login
 */
export default class Login extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true,
        }

        // Jangan lupa bind seluruh fungsi yang menggunakan state
        this.contentFinishLoading = this.contentFinishLoading.bind(this);
        this.contentStartLoading = this.contentStartLoading.bind(this);
        this.changeLoadingBody = this.changeLoadingBody.bind(this);
    }

    // jika halaman ini sudah selesai di render, hentikan preloader
    componentDidMount() {
        this.setState({
            preloader: false
        })
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

    render() {
        return (
            <>
                <Ripple time={0} customLoading={this.state.preloader} color={"white"} background={"linear-gradient(180deg, rgba(2,0,36,1) 0%, rgba(127,63,152,1) 100%);"} />
                <LoadingOverlay
                    active={this.state.contentLoading}
                    spinner
                    text={this.state.loadingBody}
                    className="d-flex w-100"
                >
                    <SirioSideNav loginMode classes="d-md-block d-none" />
                    <div className="flex-grow-1 p-5 bg-light">
                        <div className="p-5">
                            <LogoTag dark includeBack />
                            <LoginForm history={this.props.history} changeLoadingBody={this.changeLoadingBody} contentStartLoading={this.contentStartLoading} contentFinishLoading={this.contentFinishLoading} />
                        </div>
                    </div>
                </LoadingOverlay>
            </>
        )
    }
}