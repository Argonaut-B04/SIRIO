import React from "react";
import FormTambahKantorCabang from "../../Components/Form/KantorCabang/FormTambahKantorCabang";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class TambahKantorCabang extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormTambahKantorCabang />
            </SirioMainLayout>
        );
    }
};