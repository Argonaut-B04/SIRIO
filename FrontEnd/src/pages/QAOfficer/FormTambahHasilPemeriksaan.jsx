import React from "react";
import FormTambah from "../../Components/Form/HasilPemeriksaan/HasilPemeriksaanFormTambah";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormTambahHasilPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambah />
            </SirioMainLayout>
        );
    }
};