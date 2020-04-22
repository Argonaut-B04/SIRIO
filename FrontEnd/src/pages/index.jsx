import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";

/**
 * Controller untuk menampilkan halaman utama
 */
export default class MainPage extends React.Component {

    constructor(props) {
        super(props);

        // state untuk menjalankan preloader saat halaman diakses
        this.state = {
            preloader: true
        }
    }

    // jika sudah selesai dirender, hentikan preloader
    componentDidMount() {
        this.setState({
            preloader: false
        })
    }

    render() {
        return (
            <SirioMainLayout preloader={this.state.preloader}>
                <h3>Welcome to SIRIO</h3>
            </SirioMainLayout>
        )
    }
}