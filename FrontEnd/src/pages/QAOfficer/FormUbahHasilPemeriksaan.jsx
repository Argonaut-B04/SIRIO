import React from "react";
import FormUbah from "../../Components/Form/HasilPemeriksaan/HasilPemeriksaanFormUbah";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormUbahHasilPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbah />
            </SirioMainLayout>
        );
    }
};