import React, { Component } from 'react'
import SirioMainLayout from '../../Layout/SirioMainLayout'
import ErrorDetail from '../../Components/Error/ErrorDetail'

export class Error401 extends Component {

    render() {
        const detail = this.props.location.state ? this.props.location.state.detail : "Anda tidak memiliki akses ke halaman tersebut";
        return (
            <SirioMainLayout transparent>
                <ErrorDetail
                    code={401}
                    detail={detail}
                />
            </SirioMainLayout>
        )
    }
}

export default Error401
