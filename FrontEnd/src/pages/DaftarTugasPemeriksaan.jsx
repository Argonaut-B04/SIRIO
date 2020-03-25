import React from "react";
import TabelTugasPemeriksaan from "../Components/Tables/TugasPemeriksaan/TabelTugasPemeriksaan";
import SirioMainLayout from "../Layout/SirioMainLayout";

export default class DaftarTugasPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TabelTugasPemeriksaan />
            </SirioMainLayout>
        );
    }
};