import React from "react";
import TableRekomendasi from "../Components/Tables/Rekomendasi/TabelRekomendasi";
import SirioMainLayout from "../Layout/SirioMainLayout";

export default class DaftarRekomendasi extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TableRekomendasi />
            </SirioMainLayout>
        );
    }
};