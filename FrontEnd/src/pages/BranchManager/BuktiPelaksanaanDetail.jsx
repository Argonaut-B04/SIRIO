import React from "react";
import DetailBuktiPelaksanaan from "../../Components/DetailPages/BuktiPelaksanaan/DetailBuktiPelaksanaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class BuktiPelaksanaanDetail extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailBuktiPelaksanaan />
            </SirioMainLayout>
        );
    }
};