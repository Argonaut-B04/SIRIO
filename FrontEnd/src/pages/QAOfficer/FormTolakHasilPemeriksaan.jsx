import React from "react";
import FormTolak from "../../Components/Form/HasilPemeriksaan/HasilPemeriksaanFormTolak";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormTolakHasilPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTolak />
            </SirioMainLayout>
        );
    }
};