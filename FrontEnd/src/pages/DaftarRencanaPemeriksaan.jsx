import React from "react";
import TabelRencanaPemeriksaan from "../Components/Tables/RencanaPemeriksaan/TabelRencanaPemeriksaan";
import SirioMainLayout from "../Layout/SirioMainLayout";

export default class DaftarRencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelRencanaPemeriksaan />
            </SirioMainLayout>
        );
    }
};