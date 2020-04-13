import React from "react";
import DetailRencanaPemeriksaan from "../../Components/DetailPages/RencanaPemeriksaan/DetailRencanaPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class RencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DetailRencanaPemeriksaan />
            </SirioMainLayout>
        );
    }
};