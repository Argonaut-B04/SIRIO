import React from "react";
import DetailBuktiBM from "../../Components/DetailPages/BuktiPelaksanaan/DetailBukti";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DetailBuktiPelaksanaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailBuktiBM />
            </SirioMainLayout>
        );
    }
};