import React from "react";
import TableRekomendasiBM from "../../Components/Tables/Rekomendasi/TabelRekomendasiBM";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarRekomendasi extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TableRekomendasiBM />
            </SirioMainLayout>
        );
    }
};