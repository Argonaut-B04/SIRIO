import React, { Component } from 'react'
import SirioMainLayout from '../Layout/SirioMainLayout'
import DemoForm from '../Components/Form/DemoForm/DemoForm'

export class DemoFormPage extends Component {
    render() {
        return (
            <SirioMainLayout>
                <DemoForm />
            </SirioMainLayout>
        )
    }
}

export default DemoFormPage
