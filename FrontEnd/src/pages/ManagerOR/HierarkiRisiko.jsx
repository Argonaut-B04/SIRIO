import React from "react";
import FormHierarkiRisiko from "../../Components/Form/HierarkiRisiko/FormHierarkiRisiko";
import SirioMainLayout from "../../Layout/SirioMainLayout";

/**
 * Controller yang menampilkan halaman daftar risiko
 */
export default class HierarkiRisiko extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormHierarkiRisiko />
            </SirioMainLayout>
        );
    }
};