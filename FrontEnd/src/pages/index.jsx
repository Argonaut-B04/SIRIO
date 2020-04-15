import React from "react";
import AuthenticationService from "../Services/AuthenticationService";
import SirioMainLayout from "../Layout/SirioMainLayout";

/**
 * Controller untuk menampilkan halaman utama
 */
export default class MainPage extends React.Component {

    constructor(props) {
        super(props);

        // Mengambil username dan role dari AuthenticationService
        this.state = {
            username: AuthenticationService.getUsername(),
            role: AuthenticationService.getRole()
        }
    }

    render() {
        return (
            <SirioMainLayout>
                <h3>Welcome to SIRIO</h3>
            </SirioMainLayout>
        )
    }
}