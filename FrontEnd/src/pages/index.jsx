import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";

/**
 * Controller untuk menampilkan halaman utama
 */
export default class MainPage extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <h3>Welcome to SIRIO</h3>
            </SirioMainLayout>
        ) 
    }
}