import React from "react";
import FormUbah from "../../Components/Form/Employee/EmployeeFormUbah";
import SirioMainLayout from "../../Layout/SirioMainLayout";

export default class FormUbahEmployee extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <FormUbah />
            </SirioMainLayout>
        );
    }
};