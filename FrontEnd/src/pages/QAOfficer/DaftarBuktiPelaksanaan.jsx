import React from "react";
import TableBuktiPelaksanaan from "../../Components/Tables/BuktiPelaksanaan/TabelBuktiPelaksanaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarBuktiPelaksanaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TableBuktiPelaksanaan />
            </SirioMainLayout>
        );
    }
};