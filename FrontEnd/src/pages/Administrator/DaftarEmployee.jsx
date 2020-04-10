import React from "react";
import TabelEmployee from "../../Components/Tables/Employee/TabelEmployee";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarEmployee extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelEmployee />
            </SirioMainLayout>
        );
    }
};