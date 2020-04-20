import React from "react";
import FormUbahRencanaPemeriksaan from "../../Components/Form/RencanaPemeriksaan/FormUbahRencanaPemeriksaan";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class UbahrRencanaPemeriksaan extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbahRencanaPemeriksaan />
            </SirioMainLayout>
        );
    }
};