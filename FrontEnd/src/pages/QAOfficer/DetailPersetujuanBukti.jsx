import React from "react";
import DetailPersetujuan from "../../Components/DetailPages/BuktiPelaksanaan/DetailPersetujuanBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DetailPersetujuanBukti extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailPersetujuan />
            </SirioMainLayout>
        );
    }
};