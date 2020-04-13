import React, { Component } from 'react'
import SirioMainLayout from '../../Layout/SirioMainLayout'
import ErrorDetail from '../../Components/Error/ErrorDetail'

export class Error404 extends Component {
    render() {
        return (
            <SirioMainLayout transparent>
                <ErrorDetail
                    code={404}
                    detail="Halaman Tidak Ditemukan"
                />
            </SirioMainLayout>
        )
    }
}

export default Error404
