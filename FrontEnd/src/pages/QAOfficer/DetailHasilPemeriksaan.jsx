import React from "react";
import Detail from "../../Components/DetailPages/HasilPemeriksaan/DetailHasilPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class DetailHasilPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <Detail />
            </SirioMainLayout>
        );
    }
};