import React from "react";
import TabelHasilPemeriksaan from "../../Components/Tables/HasilPemeriksaan/TabelHasilPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";
import AuthenticationService from "Services/AuthenticationService";
import { Redirect } from "react-router-dom";

export default class DaftarHasilPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {

        if (AuthenticationService.getRole() !== "QA Officer Operational Risk") {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: "Not Authorized",
                        code: "401"
                    }
                }} />
            )
        }

        return (
            <SirioMainLayout>
                <TabelHasilPemeriksaan />
            </SirioMainLayout>
        );
    }
};