import React from "react";
import FormUbahKantorCabang from "../../Components/Form/KantorCabang/FormUbahKantorCabang";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class UbahKantorCabang  extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbahKantorCabang />
            </SirioMainLayout>
        );
    }
};