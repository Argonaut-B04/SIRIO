import React from "react";
import DetailRencana from "../../Components/DetailPages/RencanaPemeriksaan/DetailRencana";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DaftarRencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailRencana />
            </SirioMainLayout>
        );
    }
};