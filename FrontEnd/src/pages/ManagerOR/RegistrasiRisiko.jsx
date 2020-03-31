import React from "react";
import TabelRisiko from "../../Components/Tables/RegistrasiRisiko/TabelRisiko";
import SirioMainLayout from "../../Layout/SirioMainLayout";

/**
 * Controller yang menampilkan halaman daftar risiko
 */
export default class RegistrasiRisiko extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelRisiko />
            </SirioMainLayout>
        );
    }
};