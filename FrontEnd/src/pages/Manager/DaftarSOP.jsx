import React from "react";
import TabelSOP from "../../Components/Tables/SOP/TabelSOP";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarSOP extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelSOP />
            </SirioMainLayout>
        );
    }
};