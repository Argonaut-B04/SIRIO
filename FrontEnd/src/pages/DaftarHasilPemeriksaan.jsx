import React from "react";
import TabelHasilPemeriksaan from "../Components/Tables/HasilPemeriksaan/TabelHasilPemeriksaan";
import SirioMainLayout from "../Layout/SirioMainLayout";

export default class DaftarHasilPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelHasilPemeriksaan />
            </SirioMainLayout>
        );
    }
};